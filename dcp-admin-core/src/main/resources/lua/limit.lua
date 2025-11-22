-- 限流脚本
-- KEYS[1]: 限流key
-- ARGV[1]: 限流次数
-- ARGV[2]: 时间窗口(秒)

local key = KEYS[1]
local limit = tonumber(ARGV[1])
local expire_time = tonumber(ARGV[2])

-- 获取当前访问次数
local current = redis.call('get', key)

if current and tonumber(current) > limit then
    -- 已超过限流次数
    return tonumber(current)
end

-- 增加访问次数
current = redis.call('incr', key)

if tonumber(current) == 1 then
    -- 第一次访问，设置过期时间
    redis.call('expire', key, expire_time)
end

return tonumber(current)
