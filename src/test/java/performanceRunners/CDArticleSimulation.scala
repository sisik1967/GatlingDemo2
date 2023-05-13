package performanceRunners

import com.intuit.karate.gatling.PreDef._
import helpers.createTokens.CreateTokens
import io.gatling.core.Predef._

import scala.concurrent.duration._

class CDArticleSimulation extends Simulation
{

  val protocol = karateProtocol(
    "api/articles/{articleId}"  -> Nil
  )

  protocol.nameResolver = (req, ctx) => req.getHeader("karate-name")

  val article = csv("article.csv").circular
  val tokenFeeder = Iterator.continually(Map("token" -> CreateTokens.getNextToken))

  //val createArticle = scenario("Create An Article").exec(karateFeature("classpath:features/performance/createArticle.feature@load"))
  val createArticle = scenario("Create An Article").feed(article).feed(tokenFeeder).exec(karateFeature("classpath:features/performance/createArticleWithCSV_and_tokenFeeder.feature@load"))


  // mvn clean test-compile gatling:test -Dgatling.simulationClass=performanceRunners.CDArticleSimulation
  setUp(
    createArticle.inject(
      atOnceUsers(1), // 1 user ile simulasyon basladi
      nothingFor(4.seconds), // 4 saniye duraklama
      constantUsersPerSec(1) during (1.seconds), // 10 saniye boyunca her 1 saniyede 1 user injecte edildi
      constantUsersPerSec(2) during (1.seconds), // 10 saniye boyunca her 1 saniyede 2 user injecte edildi
      rampUsersPerSec(2) to 12 during (2.seconds), // 2 user aninda inject edildi ve ardindan 20 sn boyunca 10 user daha duzenli inject edilecek sekilde simulasyon devam etti
      nothingFor(5.seconds), // 5 saniye duraklama
      constantUsersPerSec(1) during (5.seconds) // 5 saniye boyunca her 1 saniyede 1 user injecte edildi
    ).protocols(protocol))


  // OPEN MODEL
  //setUp(
  //  createArticle.inject(
  //    nothingFor(4),
  // Pause for a given duration.
  // Verilen sure boyunca duraklar
  //    atOnceUsers(10),
  // Injects a given number of users at once
  // Verilen sayida kullaniciyi ayni anda sisteme inject eder
  //    rampUsers(10).during(5),
  // Injects a given number of users distributed evenly on a time window of a given duration
  // Verilen kullanici sayisini verilen zamana oranlar ve duzenli araliklarla kullanicilari inject eder
  //    constantUsersPerSec(20).during(15),
  // Injects users at a constant rate, defined in users per second, during a given duration. Users will be injected at regular intervals
  // Her bir saniye icin verilen kullanicisayisini  verilen sure boyunca 'duzenli araliklarla' inject eder
  //    constantUsersPerSec(20).during(15).randomized,
  // Injects users at a constant rate, defined in users per second, during a given duration. Users will be injected at randomized intervals
  // Yukaridaki islem 'duzenli araliklarla' yapilmaz 1 saniye icerisinde 20 kullanici rastgele araliklarla inject edilir
  //    rampUsersPerSec(10).to(20).during(10.minutes),
  // Injects users from starting rate to target rate, defined in users per second, during a given duration. Users will be injected at regular intervals
  // 10 kullanici tek seferde sisteme inject edilerek simulasyon baslar ve sonrasinda verilen surede 20 kullaniciya ulasacak sekilde 'duzenli araliklarla' kullanici inject edilmeye devam eder
  //    rampUsersPerSec(10).to(20).during(10.minutes).randomized,
  // Injects users from starting rate to target rate, defined in users per second, during a given duration. Users will be injected at randomized intervals
  // targeta ulasana kadar inject edilecek userlar duzenli aralÄ±klarla gonderilmez
  //    stressPeakUsers(1000).during(20) /
  // Injects a given number of users following a smooth approximation of the heaviside step function stretched to a given duration
  //  ).protocols(protocol)
  //)

  // CLOSED MODEL
  // setUp(
  //   scn.inject(
  //     constantConcurrentUsers(10).during(10),
  // Inject so that number of concurrent users in the system is constant
  //     rampConcurrentUsers(10).to(20).during(10)
  // Inject so that number of concurrent users in the system ramps linearly from a number to another
  //   )
  // )




}