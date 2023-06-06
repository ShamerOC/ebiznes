describe('Payments', () => {
    beforeEach(() => {
        cy.visit('http://localhost:3000/pay')
    })

    it('should have navigation', () => {
        cy.contains('Home')
        cy.contains('Products')
        cy.contains('Payment')
        cy.contains('Cart')
    })

    it('should Payments written', () => {
        cy.contains('Payments')
    })

    it('should have default value set to 100', () => {
        cy.contains('Amount: $100')
    })

    it('should have Pay now button', () => {
        cy.contains('Pay Now')
    })
})
