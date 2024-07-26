import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AlloyHttpDetailComponent } from './alloy-http-detail.component';

describe('AlloyHttp Management Detail Component', () => {
  let comp: AlloyHttpDetailComponent;
  let fixture: ComponentFixture<AlloyHttpDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AlloyHttpDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AlloyHttpDetailComponent,
              resolve: { alloyHttp: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AlloyHttpDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AlloyHttpDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load alloyHttp on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AlloyHttpDetailComponent);

      // THEN
      expect(instance.alloyHttp()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
