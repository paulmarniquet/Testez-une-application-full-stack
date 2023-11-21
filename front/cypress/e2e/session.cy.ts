/*
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
*/


describe('Session update spec', () => {
  beforeEach(() => {
    cy.login('yoga@studio.com', 'test!1234');

    cy.intercept('GET', '/api/session', [
        {
          "id": 2,
          "name": "session 1",
          "date": "2012-01-01T00:00:00.000+00:00",
          "teacher_id": 1,
          "description": "my description",
          "users": [
            2
          ],
          "createdAt": "2023-10-18T15:20:27",
          "updatedAt": "2023-10-18T15:20:53"
        },
        {
          "id": 3,
          "name": "ddddd",
          "date": "2023-11-20T00:00:00.000+00:00",
          "teacher_id": 1,
          "description": "ddddddd",
          "users": [],
          "createdAt": "2023-11-16T13:23:37",
          "updatedAt": "2023-11-16T13:23:37"
        }
      ]
    ).as('session');
    /*
        cy.get('.mat-card-header > .mat-focus-indicator').click();
    */

  });


  it('Update session successfully', () => {

  });
});



