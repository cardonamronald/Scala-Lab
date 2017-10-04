package Week1

  object PartialFunctions {
    val s: PartialFunction[String, String] = {
      case "ping" => "pong"
    }
    s("ping")
    s.isDefinedAt("ping")
  }