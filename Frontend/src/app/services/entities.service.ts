import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EntitiesService {
  private baseUrl = 'http://localhost:8081';

  constructor(private http: HttpClient) { }

  getConfig(entityName: string): any {
    const configs: { [key: string]: any } = {
      jobs: {
        entityName: 'jobs',
        primaryKey: 'jobId',
        columns: [
          { header: 'Job ID', field: 'jobId' },
          { header: 'Max Salary', field: 'maxSalary' },
          { header: 'Min Salary', field: 'minSalary' },
          { header: 'Job Title', field: 'jobTitle' }
        ]
      },
      employees: {
        entityName: 'employees',
        primaryKey: 'employeeId',
        columns: [
          { header: 'ID', field: 'employeeId' },
          { header: 'First Name', field: 'firstName' },
          { header: 'Last Name', field: 'lastName' },
          { header: 'Email', field: 'email' },
          { header: 'Phone Number', field: 'phoneNumber' },
          { header: 'Hire Date', field: 'hireDate' },
          { header: 'Job ID', field: 'jobId' },
          { header: 'Salary', field: 'salary' },
          { header: 'Commission Pct', field: 'commissionPct' },
          { header: 'Manager ID', field: 'managerId' },
          { header: 'Department ID', field: 'departmentId' }
        ]
      }
    };
    return configs[entityName];
  }

  getData(entityName: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/${entityName}`);
  }

  addData(entityName: string, id: any, entity: any): Observable<Object> {
    return this.http.post(`${this.baseUrl}/${entityName}/add`, entity);
  }

  updateData(entityName: string, id: any, entity: any): Observable<Object> {
    return this.http.put(`${this.baseUrl}/${entityName}/update/${id}`, entity);
  }

  deleteData(entityName: string, id: any): Observable<Object> {
    return this.http.delete(`${this.baseUrl}/${entityName}/delete/${id}`);
  }
}