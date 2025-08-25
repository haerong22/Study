GRANT ALL PRIVILEGES ON DATABASE chatdb TO chatuser;

-- 확장 모듈 활성화 (UUID 지원)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- pg_stat_statements 확장 모듈 활성화 (성능 모니터링용)
CREATE EXTENSION IF NOT EXISTS pg_stat_statements;

-- Connection pool
ALTER SYSTEM SET max_connections = '200';
ALTER SYSTEM SET shared_buffers = '256MB';
ALTER SYSTEM SET effective_cache_size = '1GB';
ALTER SYSTEM SET maintenance_work_mem = '64MB';
ALTER SYSTEM SET checkpoint_completion_target = '0.9';
ALTER SYSTEM SET wal_buffers = '16MB';
ALTER SYSTEM SET default_statistics_target = '100';

-- 설정 리로드
SELECT pg_reload_conf();

-- 커스텀 함수들 (필요시)
CREATE OR REPLACE FUNCTION update_modified_time()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$ language 'plpgsql';



/*
    CREATE TABLE messages (
        id UUID PRIMARY KEY,
        content TEXT,
        updated_at TIMESTAMP
    );

    CREATE TRIGGER trigger_update_time
    BEFORE UPDATE ON messages
    FOR EACH ROW
    EXECUTE FUNCTION update_modified_time();

 */