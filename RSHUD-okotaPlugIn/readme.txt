Okota氏作のRSHUD用プラグインをMMMが1.6.2用にリコンパイルしたものです。
権利はOkota氏に帰属しますのでご注意下さい。


＝＝＝＝＝＝＝＝＝＝＝＝＝以下オリジナル＝＝＝＝＝＝＝＝＝＝＝＝＝＝


概要
    マインクラフトさんにナニカスル事により、生体HUDを表示できるようにするMOD。
　  RSHUDのカスタマイズプラグインパッケージです。

利用条件
	・作者のMMM氏に感謝の祈りを捧げよう。
	・動画等での使用、改造、転載すきにしてもOK！
	・ただし、商用利用は除く。
	・あと、いかなる意味でも作者は責任をとりませぬ。
              ・誹謗中傷どんとこーい！（みかんの皮の汁で泣いたフリをします）

効能	[TypeB] （汎用コックピッド風味）
	　　　　　方位、速度、高度、上下角度の表示。
	　　　　　使用ツールの表示。（残使用回数＆射撃武器の残弾）
	　　　　　アーマーの装備状況
	　　　　　深度による取得可能鉱石の表示。
	　　　　　時間の表示（SPC等と異なり　日の出6：00、正午12：00、日の入り18：00、深夜0：00で表示）。
	　　　　　ポーション使用時＆腐肉＆毒の表示（ポーション表示は自己強化系のみ）

	[Raven] （アーマードコア風味）
	　　　　　上記に加えて一部語彙変更
	　　　　　アーマーの総合耐久力（AP）の表示
	　　　　　ブーストパワーの表示（満腹度を100％として速度分をマイナスしてます）
	
	本体のGUIに加え、数値カラー＆鉱石透明度部分を拡張しGUIで表示（デフォルトキー「P」）。

cfg設定	プラグイン対応により、本体[mod_RSHUD.cfg]と別に、
	[mod_RSHUD_TypeB.cfg]と[mod_RSHUD_AC.cfg]が作成され
	それぞれのカラー情報が保存できるようになりました。1粒で3度おいしいぜ！

	Line、Warning、Alert、Numberのカラー及び透明度（アルファ）は
	ゲーム内の変更GUI、又は各mod_RSHUD_**.cfgから変更できます。16進数でARGB指定です。
	取得可能鉱石の色はmod_RSHUD_**.cfg内のColor_Iron=807050等を変更してください。16進数でRGB指定です。
	（なお、鉱石表示の透明度（アルファ）はColor_Normal=cd004173の頭2文字を参照しています。）
	
使用方法
	MMM氏作成RSHUD_modと共に
	Zipファイルをmodsフォルダに入れてください。
	

2011 11/06：
MMM氏　RSHUD-1_0_0-1リリース。元は命中補正用のHUDだったらしい。

2011 12/12：
RSHUD-1_0_0-1b　ｃｆｇでのカラー変更＆取得可能鉱石の表示Ver。

2011 12/19：
MMM氏　RSHUD-1_0_0-1アップデート　
メイドさん用機能の実装試験分を追加。
各種カラーの変更GUI　（デフォルトキー「P」）と武器＆残弾の関係設定。

2012 1/2：
RSHUD-1_0_0-1b　アップデート　
時間表示の追加＆Lineの向き変更。

2012 1/4：
鉱石表示の透明度（アルファ）の変更＆微修正。

2012 1/8：
RSHUD-1_0_0-1ac アップデート 
speed系Lineの撤去。
高さ表示の変更。
アーマーポイント＆ブースターパワー表示追加。

2012 1/19：
RSHUD-1_1_0-1b、RSHUD-1_1_0-1ac アップデート
V1.1.0対応＆ポーションに一部対応（よく使う？やつ）

2012 1/21：
MMM氏　RSHUD-1_1_0-1リリース。
カスタマイズVerをプラグイン形式で取り込み可に！
すばらしぃ！

2012 1/22：
RSHUD-1_1_0_okotaPlugIn-1a　リリース。　

2012 3/06：
RSHUD-1_2_3_okotaPlugIn-1a　アップデート　

2012 4/01：
RSHUD-1_2_4_okotaPlugIn-1a　アップデート　