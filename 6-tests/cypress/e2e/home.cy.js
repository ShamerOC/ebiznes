describe('home', () => {
    beforeEach(() => {
        cy.visit('http://localhost:3000/')
    })

    it('should have navigation', () => {
        cy.contains('Home')
        cy.contains('Products')
        cy.contains('Payment')
        cy.contains('Cart')
    })

    it('should have home written', () => {
        cy.contains('Home')
    })
})
