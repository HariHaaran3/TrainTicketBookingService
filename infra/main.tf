provider "aws" {
  region = "ap-south-1"
}

resource "aws_ecs_cluster" "this" {
  name = "train-ticket-cluster"
}

resource "aws_ecs_task_definition" "this" {
  family                   = "train-ticket-task"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu                      = "512"
  memory                   = "1024"
  execution_role_arn       = "arn:aws:iam::685645913453:role/ecsTaskExecutionRole"

  container_definitions = jsonencode([
    {
      name      = "train-ticket-container"
      image     = "685645913453.dkr.ecr.ap-south-1.amazonaws.com/train-ticket-repo:latest"
      essential = true
      portMappings = [
        {
          containerPort = 9090
          hostPort      = 9090
          protocol      = "tcp"
        }
      ]
    }
  ])
}

resource "aws_ecs_service" "this" {
  name            = "train-ticket-task-service-ulo30qs6"
  cluster         = aws_ecs_cluster.this.id
  task_definition = aws_ecs_task_definition.this.arn
  launch_type     = "FARGATE"
  desired_count   = 1

  network_configuration {
    subnets          = ["subnet-0c4d0dac35e10efd6"]
    security_groups  = ["sg-0d606e6d939429d63"]
    assign_public_ip = true
  }

  deployment_controller {
    type = "ECS"
  }
}
