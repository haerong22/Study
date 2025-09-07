#!/bin/bash

# 분산 채팅 시스템 시작 스크립트
echo "🚀 Starting Distributed Chat System..."

# 환경 체크
if ! command -v docker &> /dev/null; then
    echo "❌ Docker가 설치되지 않았습니다."
    exit 1
fi

if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose가 설치되지 않았습니다."
    exit 1
fi

# 기존 컨테이너 정리 (선택적)
read -p "기존 컨테이너를 정리하시겠습니까? (y/N): " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    echo "🧹 기존 컨테이너 정리 중..."
    docker-compose down --volumes --remove-orphans
    docker system prune -f
fi

# 네트워크 및 볼륨 생성 확인
echo "🔧 Docker 네트워크 및 볼륨 확인 중..."
docker network ls | grep chat-network || docker network create chat-network

# 단계별 서비스 시작
echo "📊 데이터베이스 및 Redis 시작 중..."
docker-compose up -d postgres redis

# 헬스체크 대기
echo "⏳ 데이터베이스 준비 대기 중..."
while ! docker-compose exec postgres pg_isready -U chatuser -d chatdb; do
    sleep 2
done

echo "⏳ Redis 준비 대기 중..."
while ! docker-compose exec redis redis-cli ping; do
    sleep 2
done

# 애플리케이션 빌드 및 시작
echo "🏗️ 채팅 애플리케이션 빌드 중..."
docker-compose build --no-cache

echo "🚀 채팅 애플리케이션 인스턴스들 시작 중..."
docker-compose up -d chat-app-1 chat-app-2 chat-app-3

# 로드 밸런서 시작
echo "⚖️ Nginx 로드 밸런서 시작 중..."
docker-compose up -d nginx


# 상태 확인
echo "📊 서비스 상태 확인 중..."
docker-compose ps

echo ""
echo "✅ 분산 채팅 시스템이 성공적으로 시작되었습니다!"
echo "🔍 모니터링 명령어:"
echo "  docker-compose logs -f chat-app-1 chat-app-2 chat-app-3"
echo "  docker-compose logs -f nginx"
echo "  docker stats"
echo ""
echo "📡 Redis 모니터링 명령어:"
echo "  docker exec -it chat-redis redis-cli MONITOR          # 모든 Redis 명령 실시간 모니터링"
echo ""
echo "🛑 종료 명령어:"
echo "  docker-compose down"