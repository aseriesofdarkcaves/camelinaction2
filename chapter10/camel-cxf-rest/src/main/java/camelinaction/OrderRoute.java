package camelinaction;

import org.apache.camel.builder.RouteBuilder;

public class OrderRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // use CXF-RS to setup the REST web service using the resource class
        // and use the simple binding style which is recommended to use
        // call the route based on the operation invoked on the REST web service
        from("cxfrs:http://localhost:8080?resourceClasses=camelinaction.RestOrderService&bindingStyle=SimpleConsumer")
                .log("Incoming message!")
                .recipientList(simple("direct:${header.operationName}"));

        // routes that implement the REST services
        from("direct:createOrder")
            .bean("orderService", "createOrder");

        from("direct:getOrder")
            .bean("orderService", "getOrder(${header.id})");

        from("direct:updateOrder")
            .bean("orderService", "updateOrder");

        from("direct:cancelOrder")
            .bean("orderService", "cancelOrder(${header.id})");
    }
}
