output "alb_dns_name" {
  value = aws_lb.this.dns_name
}

output "ecr_repo_uri" {
  value = aws_ecr_repository.this.repository_url
}

output "ecs_cluster_name" {
  value = aws_ecs_cluster.this.name
}

output "ecs_service_name" {
  value = aws_ecs_service.this.name
}
