describe('Cart', () => {
    beforeEach(() => {
        cy.visit('http://localhost:3000/cart')
    })

    it('should have navigation', () => {
        cy.contains('Home')
        cy.contains('Products')
        cy.contains('Payment')
        cy.contains('Cart')
    })

    it('should be empty', () => {
        cy.contains('Your cart is empty')
    })

    it('should show one product after adding one product', () => {
        cy.visit('http://localhost:3000/products')
        cy.wait(100)
        cy.get('li')
            .contains('Add to cart')
            .closest('button')
            .click()
        cy.wait(100)
        cy.contains('Cart')
            .click()
        cy.wait(100)
        cy.contains('iPhone X')
    })

    it('should show one product after adding one product and after deletion should show none', () => {
        cy.visit('http://localhost:3000/products')
        cy.wait(100)
        cy.get('li')
            .contains('Add to cart')
            .closest('button')
            .click()
        cy.wait(100)
        cy.contains('Cart')
            .click()
        cy.wait(100)
        cy.contains('iPhone X')
        cy.contains('Remove')
            .click()
        cy.wait(100)
        cy.contains('Your cart is empty')
    })

    it('should show two product after adding two products and after deletion should show none', () => {
        cy.visit('http://localhost:3000/products')
        cy.wait(100)
        cy.get('li')
            .contains('Add to cart')
            .closest('button')
            .click()

        cy.get('li')
            .contains('Add to cart')
            .closest('button')
            .click()

        cy.wait(100)
        cy.contains('Cart')
            .click()
        cy.wait(100)
        cy.contains('iPhone X')
        cy.contains('Clear Cart')
            .closest('button')
            .click()
        cy.wait(100)
        cy.contains('Your cart is empty')
    })

    it('should show two products after', () => {
        cy.visit('http://localhost:3000/products')
        cy.wait(100)
        cy.get('li')
            .contains('Add to cart')
            .closest('button')
            .click()

        cy.contains('Samsung Galaxy S10')
            .parent()
            .contains('Add to cart')
            .click()

        cy.wait(100)
        cy.contains('Cart')
            .click()
        cy.wait(100)
        cy.contains('iPhone X')
        cy.contains('Samsung Galaxy S10')
    })

    it('should add product twice', () => {
        cy.visit('http://localhost:3000/products')
        cy.wait(100)
        cy.get('li')
            .contains('Add to cart')
            .closest('button')
            .click()

        cy.get('li')
            .contains('Add to cart')
            .closest('button')
            .click()

        cy.wait(100)
        cy.contains('Cart')
            .click()
        cy.wait(100)
        cy.contains('iPhone X')
    })

    it('should add product and show proper price', () => {
        cy.visit('http://localhost:3000/products')
        cy.wait(100)
        cy.get('li')
            .contains('Add to cart')
            .closest('button')
            .click()
        cy.wait(100)
        cy.contains('Cart')
            .click()
        cy.wait(100)
        cy.contains('999')
    })

    it('should add product twice and show proper price', () => {
        cy.visit('http://localhost:3000/products')
        cy.wait(100)
        cy.get('li')
            .contains('Add to cart')
            .closest('button')
            .click()

        cy.get('li')
            .contains('Add to cart')
            .closest('button')
            .click()

        cy.wait(100)
        cy.contains('Cart')
            .click()
        cy.wait(100)
        cy.contains('$1998')
    })



})