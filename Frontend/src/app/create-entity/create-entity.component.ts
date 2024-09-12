import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, TitleStrategy } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EntitiesService } from '../services/entities.service';

@Component({
  selector: 'app-create-entity',
  templateUrl: './create-entity.component.html',
  styleUrl: './create-entity.component.css'
})
export class CreateEntityComponent implements OnInit {
  entityName!: string;
  entityId: any;
  entityForm: any;
  config: any;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private form: FormBuilder,
    private entityService: EntitiesService
  ) { }
  
  ngOnInit(): void {
    this.entityName = this.route.snapshot.paramMap.get('entityName')!;
    this.entityId = this.route.snapshot.paramMap.get('entityId');
    this.config = this.entityService.getConfig(this.entityName);
    this.entityForm = this.form.group({});
    
    this.config.columns.forEach((column: any) => {
      this.entityForm.addControl(column.field, this.form.control('', Validators.required));
    }); 

    this.entityService.getData(this.entityName).subscribe(data => {
      const entity = data.find((item: any) => item[this.config.primaryKey] === this.entityId);
      if(entity) {
        this.entityForm.patchValue(entity);
      }
    });
  }

  onSubmit(): void {
    if (this.entityForm.valid) {
      this.entityService.addData(this.entityName, this.entityId, this.entityForm.value).subscribe({
        next: () => this.router.navigate([`/${this.entityName}`]),
        error: (e) => console.error('Error creating entity', e)
      })
    }
  }
}
