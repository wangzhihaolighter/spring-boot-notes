-- KEYS[1]代表自增key值
-- ARGV[1]代表过期时间
local c = redis.call('incr', KEYS[1])
if tonumber(c) == 1 then
    c = redis.call('expire', KEYS[1], ARGV[1])
    return c
end
return c