import { TestBed } from '@angular/core/testing';

import { ContactListService } from './contact-list.service';

describe('ContactlistService', () => {
  let service: ContactListService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ContactListService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
