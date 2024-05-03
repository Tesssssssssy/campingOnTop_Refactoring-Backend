package com.example.campingontop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Configuration
public class TransactionManager extends JpaTransactionManager {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        if (definition.getName().contains("house")) {
            System.out.println(String.format("Init. Transaction for MyApp Service: %s : %s", definition.getName(),
                    definition));
            TransactionSynchronizationManager.setCurrentTransactionIsolationLevel(
                    definition.getIsolationLevel() != TransactionDefinition.ISOLATION_DEFAULT
                            ? definition.getIsolationLevel()
                            : null);
            TransactionSynchronizationManager.setCurrentTransactionReadOnly(definition.isReadOnly());
            TransactionSynchronizationManager.setCurrentTransactionName(definition.getName());
        }
        super.doBegin(transaction, definition);
    }
}
