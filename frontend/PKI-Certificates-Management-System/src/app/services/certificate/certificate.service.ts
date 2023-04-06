import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Certificate } from 'src/app/model/certificate';

@Injectable({
  providedIn: 'root',
})
export class CertificateService {
  apiHost: string = 'http://localhost:8080/';
  headers: HttpHeaders = new HttpHeaders({
    'Content-Type': 'application/json',
  });

  constructor(private http: HttpClient) {}

  getCertificates(): Observable<Certificate[]> {
    return this.http.get<Certificate[]>(this.apiHost + 'api/certificate', {
      headers: this.headers,
    });
  }

  getCertificatesByUserId(userId: string): Observable<Certificate[]> {
    return this.http.get<Certificate[]>(this.apiHost + 'api/user/getUserCertificates/' + userId, {
      headers: this.headers,
    });
  }

  revokeCertificate(alias: string): any {
    let params = new HttpParams();
    params = params.append('alias', alias);
    return this.http.put(this.apiHost + 'api/certificate/' + alias, {});
  }
}
