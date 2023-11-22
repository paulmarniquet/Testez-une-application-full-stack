describe('Session creation spec', () => {
  beforeEach(() => {
    cy.login('yoga@studio.com', 'test!1234');


    cy.intercept('GET', '/api/teacher', {
      body: [
        {
          id: 1,
          firstName: 'Margot',
          lastName: 'DELAHAYE',
        }
      ]
    },);


    cy.get('.mat-card-header > .mat-focus-indicator').click();

  });


  it('Create session successfully', () => {

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      []).as('session')

    cy.intercept('POST', '/api/session', {
      body: {
        id: 1,
        name: 'Sandrine',
        description: 'Séance de repos',
        date: '2023-12-12',
        teacher_id: 1,
        users: [],
        createdAt: '2021-09-27T14:00:00.000Z',
        updatedAt: '2021-09-27T14:00:00.000Z',
      },
    })
    cy.get('input[formControlName=name]').type("Sandrine")
    cy.get('input[formControlName=date]').type('2023-12-12')
    cy.get('mat-select[formControlName=teacher_id]').click().get('mat-option').contains('Margot DELAHAYE').click();
    cy.get('textarea[formControlName=description]').type(`${"Séance de repos"}{enter}{enter}`)
    cy.get('button[type=submit]').click();
    cy.get('.mat-simple-snackbar').should('contain', 'Session created !');
  });

  it('Empty name', () => {
    cy.get('input[formControlName=date]').type('2023-12-12')
    cy.get('mat-select[formControlName=teacher_id]').click().get('mat-option').contains('Margot DELAHAYE').click();
    cy.get('textarea[formControlName=description]').type(`${"Séance de repos"}{enter}{enter}`)
    cy.get('button[type=submit]').should('be.disabled');
  });

  it('Empty date', () => {
    cy.get('input[formControlName=name]').type("Sandrine")
    cy.get('mat-select[formControlName=teacher_id]').click().get('mat-option').contains('Margot DELAHAYE').click();
    cy.get('textarea[formControlName=description]').type(`${"Séance de repos"}{enter}{enter}`)
    cy.get('button[type=submit]').should('be.disabled');
  });

  it('Empty teacher', () => {
    cy.get('input[formControlName=name]').type("Sandrine")
    cy.get('input[formControlName=date]').type('2023-12-12')
    cy.get('textarea[formControlName=description]').type(`${"Séance de repos"}{enter}{enter}`)
    cy.get('button[type=submit]').should('be.disabled');
  });

  it('Empty description', () => {
    cy.get('input[formControlName=name]').type("Sandrine")
    cy.get('input[formControlName=date]').type('2023-12-12')
    cy.get('mat-select[formControlName=teacher_id]').click().get('mat-option').contains('Margot DELAHAYE').click();
    cy.get('button[type=submit]').should('be.disabled');
  });
});


describe('Session update spec', () => {
  beforeEach(() => {

    cy.intercept('GET', '/api/session/1', {
      body: {
        "id": 1,
        "name": "Séance de repos",
        "date": "2023-12-12T10:00:00.000+00:00",
        "teacher_id": 1,
        "description": "On se repose ici",
        "users": [2],
        "createdAt": "2023-11-18T11:12:11",
        "updatedAt": "2023-11-18T11:12:11"
      }
    }).as('session1');

    cy.intercept('GET', '/api/teacher/1', {
      body: {
        id: 1,
        lastName: "DELAHAYE",
        firstName: "Margot",
        createdAt: "2023-08-28T11:18:31",
        updatedAt: "2023-08-28T11:18:31"
      }
    }).as('teacher');
  });


  it('Detail a session', () => {
    cy.visit('/sessions');
    cy.login('yoga@studio.com', 'test!1234');
    cy.get('@session');
    cy.contains('Detail').click();
    cy.wait('@teacher');
    cy.get('button[color="warn"]').should('contain', 'Delete');
    cy.get('div[class="description"]').should('contain', 'On se repose ici');
  });


  it('Update a session', () => {

    cy.intercept('PUT', '/api/session/1', {
      body: {
        "id": 1,
        "name": "Séance de repos 2",
        "date": "2023-12-12T10:00:00.000+00:00",
        "teacher_id": 1,
        "description": "On se repose ici",
        "users": [2],
        "createdAt": "2023-11-18T11:12:11",
        "updatedAt": "2023-11-18T11:12:11"
      }
    }).as('sessionUpdated');

    cy.visit('/sessions');
    cy.login('yoga@studio.com', 'test!1234');
    cy.get('@session');
    cy.contains('Edit').click();
    cy.get('input[formControlName=name]').clear().type('Séance de repos 2');
    cy.get('button[type=submit]').click();
    cy.wait('@sessionUpdated');
    cy.get('.mat-simple-snackbar').should('contain', 'Session updated !');
  });

  it('Invalid field during update', () => {
    cy.visit('/sessions');
    cy.login('yoga@studio.com', 'test!1234');
    cy.get('@session');
    cy.contains('Edit').click();
    cy.get('input[formControlName=name]').clear();
    cy.get('button[type=submit]').should('be.disabled');
  });


  it('Delete a session', () => {

      cy.intercept('DELETE', '/api/session/1', {
        statusCode: 200
      }).as('sessionDeleted');

      cy.visit('/sessions');
    cy.login('yoga@studio.com', 'test!1234');
    cy.get('@session');
    cy.contains('Detail').click();
    cy.get('button[color="warn"]').click();
    cy.wait('@sessionDeleted');
    cy.get('.mat-simple-snackbar').should('contain', 'Session deleted !');
  });
});

describe('Account informations', () => {
  beforeEach(() => {
    cy.login('yoga@studio.com', 'test!1234');

    cy.intercept('GET', '/api/user/1', {
      body: {
      "id": 1,
      "email": "yoga@studio.com",
      "lastName": "Admin",
      "firstName": "Admin",
      "admin": true,
      "createdAt": "2023-10-18T15:05:56",
      "updatedAt": "2023-10-18T15:05:56"

      }
    }).as('user');
  });

    it('Display account informations', () => {
      cy.get('[routerlink="me"]').click();
      cy.url().should('include', '/me');
      cy.get('@user');
      cy.get('.mat-card-content > div.ng-star-inserted > :nth-child(1)').should('contain', 'Name: Admin ADMIN');
      cy.get('.mat-card-content > div.ng-star-inserted > :nth-child(2)').should('contain', 'Email: yoga@studio.com');
      cy.get('.mat-card-content > div.ng-star-inserted > :nth-child(3)').should('contain', 'You are admin');
    });
});
