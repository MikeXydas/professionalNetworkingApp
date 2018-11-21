import { TestBed, inject } from '@angular/core/testing';

import { GetuserService } from './getuser.service';

describe('GetuserService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [GetuserService]
    });
  });

  it('should be created', inject([GetuserService], (service: GetuserService) => {
    expect(service).toBeTruthy();
  }));
});
