import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 30,
    duration: '90s',
};

// API URL 및 테스트 데이터 설정
const BASE_URL = 'http://localhost:8080';
const API_ENDPOINT = '/items';

export default function () {

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    // POST 요청 전송
    const res = http.get(`${BASE_URL}${API_ENDPOINT}`, params);

    // 응답 검증
    check(res, {
        'status is 200': (r) => r.status === 200, // 성공 상태 확인
        'no OptimisticLockException': (r) =>
            !r.body.includes('OptimisticLockException'), // 예외 발생 여부 확인
    });

    // 사용자 요청 간 대기 시간
    sleep(1);

}