package com.bookloop.bookloop.enums;

public enum PaymentStatus {
    PENDENTE,   // Pagamento aguardando confirmação
    PAGO,      // Pagamento concluído com sucesso
    RECUSADO,    // Pagamento recusado ou com erro
    CANCELADO  // Pagamento cancelado antes de ser processado
}
