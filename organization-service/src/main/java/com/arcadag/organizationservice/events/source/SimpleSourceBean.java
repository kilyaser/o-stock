package com.arcadag.organizationservice.events.source;

import com.arcadag.organizationservice.model.ActionEnum;
import com.arcadag.events.model.OrganizationChangeModel;
import com.arcadag.organizationservice.utils.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class SimpleSourceBean {
    private final KafkaTemplate<String, OrganizationChangeModel> kafkaTemplate;

    public void publishOrganizationChange(ActionEnum action, String organizationId) {
        log.debug("Sending Kafka message {} for Organization Id: {}", action, organizationId);

        OrganizationChangeModel change = new OrganizationChangeModel(
                OrganizationChangeModel.class.getTypeName(),
                action.toString(),
                organizationId,
                UserContext.getCorrelationId()
        );
        log.debug("change: {}", change);
        kafkaTemplate.send("orgChangeTopic", change);

//        source.output().send(MessageBuilder
//                .withPayload(change)
//                .build());


    }

}
