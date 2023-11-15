describe('Register spec', () => {
  it('Register successfull', () => {
    cy.visit('/register')

    cy.intercept(
      {
        method: 'GET',
        url: '/api/login',
      },
      []).as('login')

    cy.get('input[formControlName=email]').type("yogazer@studio.com")
    cy.get('input[formControlName=firstName]').type("yoga")
    cy.get('input[formControlName=lastName]').type("studio")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
    cy.intercept('POST', '/api/auth/register', {})

    cy.url().should('include', '/login')
  })
});
