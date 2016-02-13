package pl.newicom.dddd.monitoring

import akka.actor.ActorRef
import akka.contrib.pattern.ReceivePipeline.Inner
import pl.newicom.dddd.aggregate.{AggregateRootBase, EventMessageFactory}
import pl.newicom.dddd.eventhandling.EventHandler
import pl.newicom.dddd.messaging.command.CommandMessage
import pl.newicom.dddd.messaging.event.OfficeEventMessage
import pl.newicom.dddd.monitoring.Stage.Handling_Of_Command

trait AggregateRootMonitoring extends EventHandler with EventMessageFactory with TraceContextSupport {
  this: AggregateRootBase =>

  override abstract def handle(senderRef: ActorRef, event: OfficeEventMessage): Unit = {
    super.handle(senderRef, event)
    finishCurrentTraceContext()
    log.debug("Event stored: {}", event.payload)
  }

  pipelineOuter {
    case cm: CommandMessage =>
      log.debug("Received: {}", cm)
      setNewCurrentTraceContext(Handling_Of_Command.traceContextName(officeId, cm))
      Inner(cm)
  }

  def commandTraceContext = currentTraceContext
}
