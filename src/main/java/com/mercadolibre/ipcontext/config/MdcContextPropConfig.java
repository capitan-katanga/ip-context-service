package com.mercadolibre.ipcontext.config;

import com.mercadolibre.ipcontext.util.Constants;
import io.micrometer.context.ContextRegistry;
import io.micrometer.context.ContextSnapshotFactory;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Hooks;

import java.util.List;

@Configuration
@ConditionalOnClass({ContextRegistry.class, ContextSnapshotFactory.class})
public class MdcContextPropConfig {

    public MdcContextPropConfig() {
        var fields = List.of(Constants.CORRELATION_ID, Constants.SPAN_ID);
        fields.forEach(claim -> ContextRegistry.getInstance()
                .registerThreadLocalAccessor(claim,
                        () -> MDC.get(claim),
                        value -> MDC.put(claim, value),
                        () -> MDC.remove(claim)));
        Hooks.enableAutomaticContextPropagation();
    }
}
