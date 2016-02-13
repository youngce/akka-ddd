package pl.newicom.dddd.monitoring

import pl.newicom.dddd.aggregate.BusinessEntity
import pl.newicom.dddd.messaging.AddressableMessage

case class Stage(position: Integer, shortName: String) {

  def traceContextName(observed: BusinessEntity, msg: AddressableMessage): String =
    s"$position-${observed.department.toUpperCase}-$shortName-${msg.payloadName}"
}

object Stage {
  val Handling_Of_Command = Stage(1, "handling")
  val Reception_Of_Event  = Stage(2, "reception")
  val Reaction_On_Event   = Stage(3 ,"reaction")
}
