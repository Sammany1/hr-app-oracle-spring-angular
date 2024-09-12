import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DynamicEntityComponent } from './dynamic-entity/dynamic-entity.component';
import { UpdateEntityComponent } from './update-entity/update-entity.component';

const routes: Routes = [
  { path: ':entityName/update/:id', component: UpdateEntityComponent },
  { path: ':entityName', component: DynamicEntityComponent },
  { path: '', redirectTo: '/jobs', pathMatch: 'full' } // Default route
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }