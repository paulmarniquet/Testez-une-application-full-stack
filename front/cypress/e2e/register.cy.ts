describe('Register spec', () => {

  beforeEach(() => {
    cy.visit('/register')
    cy.intercept(
      {
        method: 'GET',
        url: '/api/login',
      },
      []).as('login')
  });


  it('Register successfull', () => {

    cy.intercept('POST', '/api/auth/register', {
      body: {}
    });

    cy.get('input[formControlName=email]').type("yogazer@studio.com")
    cy.get('input[formControlName=firstName]').type("yoga")
    cy.get('input[formControlName=lastName]').type("studio")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
    cy.url().should('include', '/login')
  })

  it('Register failed', () => {
    cy.intercept('POST', '/api/auth/register', {
      statusCode: 401,
      body: {
        message: 'Unauthorized'
      }
    });
    cy.get('input[formControlName=email]').type("yogazer@studio.com")
    cy.get('input[formControlName=firstName]').type("yoga")
    cy.get('input[formControlName=lastName]').type("studio")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
    cy.get('.error').should('contain', 'An error occurred');
  });

  it('Invalid/Empty First name', () => {
    cy.get('input[formControlName=email]').type("yogazer@studio.com")
    cy.get('input[formControlName=lastName]').type("studio")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
    cy.get('button[type=submit]').should('be.disabled');
  });

  it('Invalid/Empty Last name', () => {
    cy.get('input[formControlName=email]').type("yogazer@studio.com")
    cy.get('input[formControlName=firstName]').type("yoga")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
    cy.get('button[type=submit]').should('be.disabled');
  });

  it('Invalid/Empty Password', () => {
    cy.get('input[formControlName=email]').type("yogazer@studio.com")
    cy.get('input[formControlName=firstName]').type("yoga")
    cy.get('input[formControlName=lastName]').type("studio")
    cy.get('button[type=submit]').should('be.disabled');
  });

  it('Invalid/Empty Email', () => {
    cy.get('input[formControlName=firstName]').type("yoga")
    cy.get('input[formControlName=lastName]').type("studio")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
    cy.get('button[type=submit]').should('be.disabled');
  });
});
