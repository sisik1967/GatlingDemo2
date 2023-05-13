Feature: Eval feature

  Scenario:
    * def dateStringToLong =
  """
  function(s) {
    var SimpleDateFormat = Java.type('java.text.SimpleDateFormat');
    var sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    return sdf.parse(s).time; // '.getTime()' would also have worked instead of '.time'
    karate.log('Eval keyword')
  }
  """
    * def greeter = function(title, name) { return 'hello ' + title + ' ' + name ; console.log(title)}
    * eval greeter('Qa Engineer','Ayşe')