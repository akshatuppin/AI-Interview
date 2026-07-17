import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (request, next) => {

  const token = localStorage.getItem("token");

  if(token){
    request = request.clone({
      setHeaders:{
        Auhtorization: `Bearer ${token}`
      }
    });
  }

  return next(request);
};
  