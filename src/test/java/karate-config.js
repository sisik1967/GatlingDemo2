function fn() {
  var env = karate.env; // get java system property 'karate.env'
  karate.log('karate.env system property was:', env);
  if (!env) {
    env = 'dev'; // a custom 'intelligent' default
  }
  var config = { // base config JSON
    appId: 'my.app.id',
    appSecret: 'my.secret',
    someUrlBase: 'https://some-host.com/v1/auth/',
    anotherUrlBase: 'https://another-host.com/v1/',
    baseUrl: 'https://conduit.productionready.io/api/'

  };
  if (env == 'stage') {

    // over-ride only those that need to be
    config.someUrlBase = 'https://stage-host/v1/auth';
  }

  else if  (env == 'qa' || env == 'prod')
  {
      // customize
      // e.g. config.foo = 'bar';
      config.userEmail = 'tdemailtestdata@gmail.com'
      config.userPassword = 'Trendyol123!'
  }
  else if (env == 'e2e')
  {
      config.someUrlBase = 'https://e2e-host/v1/auth';
  }
  else if (env == 'dev')
   {
      // customize
      config.userEmail = 'emryduman@gmail.com'
      config.userPassword = 'Condui123'
   }
  // don't waste time waiting for a connection or if servers don't respond within 5 seconds
  karate.configure('connectTimeout', 5000);
  karate.configure('readTimeout', 5000);
  return config;
}