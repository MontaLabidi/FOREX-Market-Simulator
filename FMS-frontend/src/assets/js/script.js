export function TradingViewwidget() {
  new TradingView.widget(
    {

      "width": "auto",
      "height": 400,
      "symbol": "FX:EURUSD",
      "interval": "D",
      "timezone": "Etc/UTC",
      "theme": "Light",
      "style": "1",
      "locale": "en",
      "toolbar_bg": "#f1f3f6",
      "enable_publishing": false,
      "withdateranges": true,
      "hide_side_toolbar": false,
      "allow_symbol_change": true,
      "container_id": "tradingview_75d53"
    }
  );
}
