describe('products', () => {
    beforeEach(() => {
        cy.visit('http://localhost:3000/products')
    })

    it('should have navigation', () => {
        cy.contains('Home')
        cy.contains('Products')
        cy.contains('Payment')
        cy.contains('Cart')
    })

    it('should have more than one item', () => {
        cy.get('.products').should('have.length.gte', 1)
    })

    it('Should have iPhone X listed', () => {
        cy.get('li')
            .contains('iPhone X')
    })

    it('Should have price listed', () => {
        cy.get('li')
            .contains('Cena: 999 zł')
    })

    it('Should have button', () => {
        cy.get('li')
            .contains('Add to cart')
    })

    it('Should have lista produktow', () => {
        cy.contains('Lista produktów:')
    })

})
