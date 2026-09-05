import { Component, inject, signal } from '@angular/core';
import { MembersService } from '../../../core/services/members/members';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProjectRole } from '../../../core/models/projectMember.models';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-members-add',
  imports: [
    ReactiveFormsModule,
  ],
  templateUrl: './members-add.html',
  styleUrl: './members-add.css',
})
export class MembersAdd {
  private membersService = inject(MembersService);
  private fb = inject(FormBuilder);
  private route = inject(ActivatedRoute);
  
  projectId: number | null = null;
  successMsg = signal('');
  errorMsg = signal('')
  
  addMemberForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    role: [ProjectRole.MEMBER, Validators.required],
  });

  roles = Object.values(ProjectRole);

  ngOnInit(): void{
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if(!id){
      this.errorMsg.set('Invalid project ID');
      return;
    }
    this.projectId = id;

  }

  onSubmit(): void {
    this.successMsg.set('');
    this.errorMsg.set('');

    if (this.addMemberForm.invalid) {
      this.errorMsg.set('Please fill in all required fields correctly.');
      return;
    }
    const { email, role } = this.addMemberForm.value;
    const request = { email: email!, role: role! };
    this.membersService.addMember(this.projectId!, request).subscribe({
      next: () => {
        this.successMsg.set('Member added successfully.');
        this.addMemberForm.reset({
        email: '',
        role: ProjectRole.MEMBER
      });
      },
      error: (error) => {
        this.errorMsg.set(error.error?.message || 'An error occurred while adding the member.');
        console.error('Error adding member:', error);
      }
    });
  }
}
