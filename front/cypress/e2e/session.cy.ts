describe('Session spec', () => {
    beforeEach(() => {
        cy.login('yoga@studio.com', 'test!1234');
    });

    it('Create session successfully', () => {
        cy.get('.mat-card-header > .mat-focus-indicator').click();

    });
});

