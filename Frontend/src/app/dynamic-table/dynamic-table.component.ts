import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { DynamicEntityComponent } from '../dynamic-entity/dynamic-entity.component';
@Component({
  selector: 'app-dynamic-table',
  templateUrl: './dynamic-table.component.html',
  styleUrls: ['./dynamic-table.component.css']
})
export class DynamicTableComponent implements OnInit {
  @Input() data?: any[];
  @Input() config: any;
  // @Output() updateData = new EventEmitter<any>();
  // @Output() deleteData = new EventEmitter<any>();

  constructor(private dynamicEntityComponent: DynamicEntityComponent) { }

  ngOnInit(): void { }

  onUpdateData(id: any): void {
    this.dynamicEntityComponent.updateData(id);
  }

  onDeleteData(id: any): void {
    this.dynamicEntityComponent.deleteData(id);
  }
}