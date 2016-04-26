var basicPluginAlreadySaidHello = false;

function BasicPlugin() {

  this.sayHello = function() {
    
    if( !basicPluginAlreadySaidHello ) {
      basicPluginAlreadySaidHello = true;
      alert( 'Hello from basic-plugin!' );
    }
  };
};

$( window ).load( function() {
  new BasicPlugin().sayHello();
}); 
