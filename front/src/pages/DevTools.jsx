import React, { useState } from "react";

function DevTools() {
  const [input, setInput] = useState("");
  const [result, setResult] = useState(null);
  const [error, setError] = useState(null);

  const handleHealthCheck = async () => {
    setError(null);
    setResult(null);

    try {
      const res = await fetch(`/api/dev/health-check?input=${encodeURIComponent(input)}`);
      if (!res.ok) throw new Error(`HTTP error ${res.status}`);
      const data = await res.json();
      if (data.error || data.dbError) {
        setError(data.error || data.dbError);
      } else {
        setResult(data);
      }
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div style={{ padding: "1rem" }}>
      <h2>개발 전용 Health Check</h2>
      <input
        type="text"
        placeholder="테스트 문자열 입력"
        value={input}
        onChange={(e) => setInput(e.target.value)}
        style={{ marginRight: "0.5rem" }}
      />
      <button onClick={handleHealthCheck}>Health Check 실행</button>

      {error && (
        <div style={{ marginTop: "1rem", padding: "1rem", background: "#ffdddd", color: "#a00" }}>
          ❌ 오류: {error}
        </div>
      )}

      {result && (
        <div style={{ marginTop: "1rem" }}>
          <div
            style={{
              padding: "1rem",
              background: "#ddffdd",
              color: "#060",
              marginBottom: "1rem",
            }}
          >
            ✅ 백엔드 처리 결과: <strong>{result.backendTest}</strong>
          </div>

          {Array.isArray(result.dbTest) && result.dbTest.length > 0 && (
            <table
              border="1"
              cellPadding="8"
              style={{ borderCollapse: "collapse", background: "#fff" }}
            >
              <thead>
                <tr>
                  {Object.keys(result.dbTest[0]).map((key) => (
                    <th key={key}>{key}</th>
                  ))}
                </tr>
              </thead>
              <tbody>
                {result.dbTest.map((row, idx) => (
                  <tr key={idx}>
                    {Object.values(row).map((val, i) => (
                      <td key={i}>{String(val)}</td>
                    ))}
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      )}
    </div>
  );
}

export default DevTools;
