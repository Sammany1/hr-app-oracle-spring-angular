import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DynamicEntityComponent } from './dynamic-entity.component';

describe('DynamicEntityComponent', () => {
  let component: DynamicEntityComponent;
  let fixture: ComponentFixture<DynamicEntityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DynamicEntityComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DynamicEntityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
