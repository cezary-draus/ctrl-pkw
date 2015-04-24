package pl.ctrlpkw.model.write;

import com.datastax.driver.mapping.Mapper;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import pl.ctrlpkw.CassandraContext;

import javax.annotation.Resource;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProtocolMapperFactory extends AbstractFactoryBean<Mapper<Protocol>> {

    @Resource
    private CassandraContext cassandraContext;

    @Override
    public Class<?> getObjectType() {
        return Mapper.class;
    }

    @Override
    protected Mapper<Protocol> createInstance() throws Exception {
        return cassandraContext.getMappingManager().mapper(Protocol.class);
    }
}
