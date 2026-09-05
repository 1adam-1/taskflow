export enum TaskStatus {
  TODO = 'TODO',
  IN_PROGRESS = 'IN_PROGRESS',
  REVIEW = 'REVIEW',
  DONE = 'DONE'
}

export enum Priority {
  LOW = 'LOW',
  MEDIUM = 'MEDIUM',
  HIGH = 'HIGH',
  URGENT = 'URGENT'
}

export interface Task {
  id: number;
  title: string;
  description: string;
  status: TaskStatus;
  priority: Priority;
  dueDate: string | null;
  projectId: number;
  assigneeId: number | null;
  assigneeName: string | null;
}

export interface CreateTaskRequest {
  title: string;
  description: string;
  status: TaskStatus; 
  priority: Priority;
  dueDate?: string;
  assigneeId?: number;
}
