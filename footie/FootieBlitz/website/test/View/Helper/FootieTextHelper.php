<?php

class FootieTextHelper extends AppHelper {
    function shortenText($text, $chars) {
        // Change to the number of characters you want to display
		$chars = $chars;
		$text = $text."";
		$countchars = strlen($text);
		if($countchars > $chars+3) {
			$text = substr($text,0,$chars);
			//$text = substr($text,0,strrpos($text,' '));
			$text = $text."...";
		}
		return $text;
    }
}
?>