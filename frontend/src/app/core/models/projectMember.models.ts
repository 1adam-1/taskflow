export enum ProjectRole {
  OWNER = 'OWNER',
  MANAGER = 'MANAGER',
  MEMBER = 'MEMBER'
}

export interface ProjectMember {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  role: ProjectRole;
}

export interface AddMemberRequest {
  email: string;
  role: ProjectRole;
}