// ============================================================
// StockReservationResult.java
// Reemplaza los eventos de Kafka StockReservedEvent y
// StockFailedEvent. En los microservicios originales el
// Inventory Service publicaba uno de estos dos eventos de
// forma asincrona y el Order Service los escuchaba con
// @KafkaListener.
//
// En el monolito no hay broker de mensajes — InventoryService
// simplemente devuelve este resultado inmediatamente cuando
// OrderService lo llama directamente. Mismo significado,
// sin la espera asincrona.
// ============================================================

package com.modex.monolith.inventory;

public record StockReservationResult(
        boolean success,
        String reason
) {
    public static StockReservationResult ok() {
        return new StockReservationResult(true, null);
    }

    public static StockReservationResult failed(String reason) {
        return new StockReservationResult(false, reason);
    }
}