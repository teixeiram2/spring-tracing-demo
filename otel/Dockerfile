FROM otel/opentelemetry-collector-contrib:latest
COPY otel-collector-config.yaml /etc/otel/otel-collector-config.yaml

ENTRYPOINT ["/otelcontribcol"]
CMD ["--config", "/etc/otel/otel-collector-config.yaml"]
