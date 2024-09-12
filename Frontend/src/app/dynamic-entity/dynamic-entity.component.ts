import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EntitiesService } from '../services/entities.service';

@Component({
  selector: 'app-dynamic-entity',
  templateUrl: './dynamic-entity.component.html',
  styleUrls: ['./dynamic-entity.component.css']
})
export class DynamicEntityComponent implements OnInit {
  entityName!: string;
  data!: any[];
  config: any;

  constructor(private entitiesService: EntitiesService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.route.url.subscribe(url => {
      this.entityName = this.route.snapshot.url[0].path;
      this.config = this.entitiesService.getConfig(this.entityName);
      this.fetchData();
    });
  }

  fetchData(): void {
    this.entitiesService.getData(this.entityName).subscribe({
      next: (data) => {
        this.data = data;
      },
      error: (e) => {
        console.error(`Error fetching data for ${this.entityName}`, e);
      }
    });
  }

  updateData(id: any): void {
    this.router.navigate([`/${this.entityName}/update/${id}`]);
  }

  deleteData(id: any): void {
    this.entitiesService.deleteData(this.entityName, id).subscribe({
      next: () => this.fetchData(),
      error: (e) => {
        console.error(`Error deleting data for ${id}`, e);
      }
    });
  }
}