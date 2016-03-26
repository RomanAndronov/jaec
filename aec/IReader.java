package aec;

/*
   By Roman Andronov
 */

class IReader
{
	IReader( Cache cache )
	{
		this.cache = cache;
	}

	void
	setInput( String infixae, AeNotation aeon )
	{
		input = infixae;
		outputNotation = aeon;
		outputNotation.rewind( input );
	}

	AeItem
	getItem()
	{
		char			ch = '\0';
		AeItem			item = null;


		/*
		   Skip white space
		 */
		while ( !outputNotation.eoi() )
		{
			ch = input.charAt( outputNotation.curr() );
			if ( isSpace( ch ) == false )
			{
				break;
			}

			if ( ch == '\n' )
			{
				outputNotation.setLn();
			}

			outputNotation.moveNext();
		}

		if ( outputNotation.eoi() )
		{
			return null;
		}

		outputNotation.setCol();

		item = cache.alloc();
		if ( AeItem.isOperator( ch ) == true )
		{
			item.mkOperator( ch, outputNotation.ln(), outputNotation.col() );
		}
		else
		{
			/*
			   If this item is not an operator
			   then it is a potential operand
			 */
			operand.setLength( 0 );

			while ( !outputNotation.eoi() )
			{
				ch = input.charAt( outputNotation.curr() );

				/*
				   White space or next operator
				   marks the end of this operand
				 */
				if ( isSpace( ch ) == true || AeItem.isOperator( ch ) == true )
				{
					/*
					   End of operand. Put this character
					   back into the input queue
					 */
					outputNotation.movePrev();
					break;
				}

				/*
				   Otherwise - keep assembling the operand
				 */
				operand.append( ch );
				outputNotation.moveNext();
			}
			outputNotation.prepareOperand( operand );
			item.mkOperand( operand.toString(),
				outputNotation.ln(),
				outputNotation.col() );
		}

		outputNotation.moveNext();

		return item;
	}

	static boolean
	isSpace( char c )
	{
		if ( c == ' ' ||
			c == '\t' ||
			c == '\f' ||
			c == '\r' ||
			c == '\n' )
		{
			return true;
		}

		return false;
	}


	private String			input = null;
	private AeNotation		outputNotation = null;
	private StringBuilder		operand = new StringBuilder();
	private Cache			cache = null;
}
