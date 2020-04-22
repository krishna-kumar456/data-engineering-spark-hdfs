package ETL

case class jobConfigClusterHandler(){
  var masterIp: String = ""
  var appName: String = ""
  var logLevel: String = ""
}

case class jobConfigSourceHandler(){
  var sourceId: String = ""
  var sourceType: String = ""
  var fileType: String = ""
  var path: String = ""
}

case class jobConfigDestinationHandler() {
  var destinationId: String = ""
  var destinationType: String = ""
  var analyticalQuery: String = ""
  var fileType: String = ""
  var path: String = ""

}