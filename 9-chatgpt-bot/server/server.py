from http.server import BaseHTTPRequestHandler, HTTPServer
from urllib.parse import parse_qs
import openai

hostName = "localhost"
serverPort = 8080

class MyServer(BaseHTTPRequestHandler):
    def do_GET(self):
        if '/chat' not in str(self.path):
            self.send_response(404)
            self.end_headers()
            return

        message = parse_qs(self.path)['/chat?message'][0]

        response = openai.Completion.create(
            model="gpt-3.5-turbo",
            prompt=message
        )

        text = response.choices[0].text

        self.send_response(200)
        self.send_header("Content-type", "text/json")
        self.send_header('Access-Control-Allow-Origin', '*')
        self.end_headers()
        self.wfile.write(bytes("{", "utf-8"))
        self.wfile.write(bytes("\"response\": ", "utf-8"))
        self.wfile.write(bytes("\"" + text + "\"", "utf-8"))
        self.wfile.write(bytes("}", "utf-8"))

if __name__ == "__main__":
    webServer = HTTPServer((hostName, serverPort), MyServer)
    print("Server started http://%s:%s" % (hostName, serverPort))
    openai.api_key = "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"
    # openai.ChatCompletion.create(
        # model="gpt-3.5-turbo",
        # api_key="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"
    # )
    try:
        webServer.serve_forever()
    except KeyboardInterrupt:
        pass

    webServer.server_close()
    print("Server stopped.")