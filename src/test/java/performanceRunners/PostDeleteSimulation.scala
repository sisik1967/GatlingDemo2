package performanceRunners

import com.intuit.karate.gatling.PreDef._
import helpers.createTokens.CreateTokens
import io.gatling.core.Predef._

import scala.concurrent.duration._

class PostDeleteSimulation extends Simulation
{

    CreateTokens.createAccessTokens()

    val protocol = karateProtocol(

        //GATLING REPORTTA TUM DELETE REQUESTLERINI 1 TAB ICINDE TOPARLADI/GOSTERDI
        "api/articles/{articleId}"-> Nil //SCALA dilinde empty list{} demektir
    )

    //protocol.nameResolver = (req, ctx) => req.getHeader("karate-name")
    //protocol.runner.karateEnv("perf")

    //Scenario Adi
    val createAndDelete = scenario("Conduit Article Website Performance Testing via Gatling - rampUsers").exec(karateFeature("classpath:features/performance/createArticle.feature@load"))
    //val createAndDelete2 = scenario("Conduit Article Website Performance Testing via Gatling - atOnceUsers").exec(karateFeature("classpath:features/performance/createArticle.feature@load"))
    //val delete = scenario("delete").exec(karateFeature("classpath:mock/cats-delete.feature@name=delete"))

    setUp(
        //1 saniyede 2 kullaniciyi sisteme dahil et - SCALA'DA SATIR SONUNA COMMENT YAZMAYIN ARKADASLAR
        //createAndDelete.inject(rampUsers(10) during (5.seconds)).protocols(protocol)
        //Tek seferde 10 kullaniciyi sisteme sok
       // createAndDelete.inject(atOnceUsers(10)).protocols(protocol)

        createAndDelete.inject
        (
            atOnceUsers(3),
            nothingFor(4.second),
            constantUsersPerSec(1) during(10.seconds),
            //constantUsersPerSec(2) during(10.seconds),
            rampUsersPerSec(2) to 12 during (20.seconds),
            nothingFor(5.seconds)

        )

      //createAndDelete.inject(rampUsers(10) during (5.seconds)).protocols(protocol)
      //delete.inject(rampUsers(5) during (5.seconds)).protocols(protocol)
    )

//mvn clean test-compile gatling:test -Dgatling.simulationClass=performanceRunners.PostDeleteSimulation
//mvn clean test-compile gatling:test -DsimulationClass=performanceRunners.PostDeleteSimulation
}
