describe('Login spec', () => {
  it('Login successfull', () => {
    cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true
      },
    })

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      []).as('session')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.url().should('include', '/sessions')
  })

  it('Login failed', () => {
    cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', {
      statusCode: 401,
      body: {
        message: 'Unauthorized'
      },
    })

    cy.get('input[formControlName=email]').type("paul.non@gmail.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
    cy.get('.error').should('contain', 'An error occurred');
  })

  it('Bad email', () => {
    cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', {
      statusCode: 401,
      body: {
        message: 'Unauthorized'
      },
    })

    cy.get('input[formControlName=email]').type("paul.nongmail")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
    cy.get('.mat-form-field-invalid').should('exist');
    cy.get('.error').should('contain', 'An error occurred');
  })

  it('Empty password', () => {
    cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', {
      statusCode: 401,
      body: {
        message: 'Unauthorized'
      },
    })

    cy.get('input[formControlName=email]').type("paul.oui@gmail")
    cy.get('input[formControlName=password]').type(`${""}{enter}{enter}`)
    cy.get('.mat-form-field-invalid').should('exist');
    cy.get('.error').should('contain', 'An error occurred');
  })

  it('Empty email', () => {
    cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', {
      statusCode: 401,
      body: {
        message: 'Unauthorized'
      },
    })

    cy.get('input[formControlName=password]').type(`${"test"}{enter}{enter}`)
    cy.get('.mat-form-field-invalid').should('exist');
    cy.get('.error').should('contain', 'An error occurred');
  })
});



describe('Logout spec', () => {
  beforeEach(() => {
    cy.login('yoga@studio.com', 'test!1234');
  });

  it('Should logout', () => {
    cy.get('.mat-toolbar > .ng-star-inserted > :nth-child(3)').click();
    cy.url().should('contain', '')
    cy.get('[routerlink="login"]').should('exist');
  });
});
