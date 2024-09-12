import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common'; 

import { AppComponent } from './app.component';
import { DynamicEntityComponent } from './dynamic-entity/dynamic-entity.component';
import { DynamicTableComponent } from './dynamic-table/dynamic-table.component'; 
import { EntitiesService } from './services/entities.service';
import { UpdateEntityComponent } from './update-entity/update-entity.component';
import { CreateEntityComponent } from './create-entity/create-entity.component';

@NgModule({
  declarations: [
    AppComponent,
    DynamicEntityComponent,
    DynamicTableComponent,
    UpdateEntityComponent,
    CreateEntityComponent 
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    CommonModule,
    ReactiveFormsModule
  ],
  providers: [EntitiesService],
  bootstrap: [AppComponent]
})
export class AppModule { }