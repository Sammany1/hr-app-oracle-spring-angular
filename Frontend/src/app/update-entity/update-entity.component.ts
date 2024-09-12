import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EntitiesService } from '../services/entities.service';

@Component({
  selector: 'app-update-entity',
  templateUrl: './update-entity.component.html',
  styleUrls: ['./update-entity.component.css']
})
export class UpdateEntityComponent implements OnInit {
  entityName!: string;
  entityId!: any;
  entityForm!: FormGroup;
  config: any;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private form: FormBuilder,
    private entitiesService: EntitiesService
  ) { }

  ngOnInit(): void {
    this.entityName = this.route.snapshot.paramMap.get('entityName')!;
    this.entityId = this.route.snapshot.paramMap.get('id')!;
    this.config = this.entitiesService.getConfig(this.entityName);

    this.entityForm = this.form.group({});
    this.config.columns.forEach((column: any) => {
      this.entityForm.addControl(column.field, this.form.control('', Validators.required));
    });

    this.entitiesService.getData(this.entityName).subscribe(data => {
      const entity = data.find((item: any) => item[this.config.primaryKey] === this.entityId);
      if (entity) {
        this.entityForm.patchValue(entity);
      }
    });
  }

  onSubmit(): void {
    if (this.entityForm.valid) {
      this.entitiesService.updateData(this.entityName, this.entityId, this.entityForm.value).subscribe({
        next: () => this.router.navigate([`/${this.entityName}`]),
        error: (e) => console.error('Error updating entity', e)
      });
    }
  }
}