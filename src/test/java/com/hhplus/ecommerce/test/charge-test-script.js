import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 10,
    iterations: 10,
};

// API URL 및 테스트 데이터 설정
const BASE_URL = 'http://localhost:8080';
const API_ENDPOINT = '/user/charge';

export default function () {
    const payload = JSON.stringify({
        userId: 1,         // 충돌을 유발하기 위해 동일한 사용자 ID 사용
        balance: 100,      // 충전 금액
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    // POST 요청 전송
    const res = http.post(`${BASE_URL}${API_ENDPOINT}`, payload, params);

    // 응답 검증
    check(res, {
        'status is 200': (r) => r.status === 200, // 성공 상태 확인
        'no OptimisticLockException': (r) =>
            !r.body.includes('OptimisticLockException'), // 예외 발생 여부 확인
    });

    // 사용자 요청 간 대기 시간
    sleep(1);

}